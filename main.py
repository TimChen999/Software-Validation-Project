import os
import json5
from pathlib import Path
from lmstudio import llm
from pydantic import BaseModel
from tqdm import tqdm
from collections import defaultdict
from tabulate import tabulate  # for the summary table
import re

# ========== CONFIGURATION ==========
JAVA_DIR = Path("testcode")
OUTPUT_DIR = Path("output")
MODELS = [
    "mistral-nemo-instruct-2407",
    "gemma-3-4b-it",
    "llama-3.2-1b-instruct",
    "deepseek-r1-distill-qwen-7b"
]
PROMPT_TEMPLATE = (
    "You are a static code analysis tool.\n"
    "Analyze the following Java code and identify bugs.\n"
    "For each bug, return a plain JSON object with the following fields:\n"
    '- \"bug\": A concise description of the bug.\n'
    '- \"line\": The line number where the bug occurs.\n'
    '- \"reason\": A brief explanation of why it is a bug.\n\n'
    "⚠️ Do NOT include any markdown formatting, no ```json, no <think>, and no extra explanations.\n"
    "Your entire response must be ONLY a raw JSON array. No intro, no outro.\n\n"
    "If there are multiple bugs, include them all in the array.\n"
    "If there are no bugs, return an empty array: []\n\n"
    "If you encounter any errors or exceptions, return an empty array: []\n\n"
    "If you cannot analyze the code, return an empty array: []\n\n"
    "If the code is too long, return an empty array: []\n\n"
    "PLEASE remember to close the array with a ]\n\n"
    "Please use the corresponding closes bracket for the opening bracket.\n\n"
    "Use the following brackets for the JSON array:\n"
    "Use [ for the opening bracket\n"
    "Use ] for the closing bracket\n\n"
    "Use opening curly braces for the JSON objects:\n"
    "Use closing curly braces for the JSON objects:\n\n"
    "The JSON array should be a valid JSON array.\n\n"
    "The JSON array should be the ONLY thing in your response.\n\n"
    "Avoid using trailing commas in the JSON.\n\n"
    "Here is the Java code to analyze:\n{code}"
)


# ========== DATA CLASS ==========
class BugReport(BaseModel):
    bug: str
    line: int
    reason: str


# ========== FUNCTIONS ==========
def extract_first_json_array(s: str) -> str:
    """
    Extracts the first top-level JSON array (e.g., starting with [ and ending with ]) from a string.
    Ignores any markdown or explanation text.
    """
    match = re.search(r"\[\s*{.*?}\s*\]", s, flags=re.DOTALL)
    if not match:
        raise ValueError("No valid JSON array found in response.")
    return match.group(0)


def save_raw_response(response_text: str, java_file: Path, model_name: str, output_dir: Path):
    raw_filename = output_dir / \
        f"{java_file.stem}_{model_name.replace('/', '_')}_RAW.txt"
    with raw_filename.open("w") as f:
        f.write(response_text)


# ========== MAIN ==========
OUTPUT_DIR.mkdir(parents=True, exist_ok=True)
java_files = list(JAVA_DIR.glob("*.java"))

summary = defaultdict(lambda: {"success": 0, "fail": 0})

for model_name in MODELS:
    print(f"\n🔁 Loading model: {model_name}")
    model = llm(model_name)

    print(f"🧠 Analyzing {len(java_files)} Java files with {model_name}...\n")

    with tqdm(total=len(java_files), desc=model_name, unit="file") as progress:
        for java_file in java_files:
            code = java_file.read_text(encoding='utf-8')
            prompt = PROMPT_TEMPLATE.format(code=code)

            try:
                response_obj = model.respond(prompt)
                response = response_obj.output if hasattr(
                    response_obj, 'output') else str(response_obj)

                # Save raw output BEFORE parsing
                save_raw_response(response, java_file, model_name, OUTPUT_DIR)

                cleaned_json = extract_first_json_array(response)
                parsed = json5.loads(cleaned_json)

                bug_reports = [BugReport(**item) for item in parsed]

                output_path = OUTPUT_DIR / \
                    f"{java_file.stem}_{model_name.replace('/', '_')}_report.json"
                with output_path.open("w") as f:
                    json5.dump([r.model_dump()
                                for r in bug_reports], f, indent=2, quote_keys=True, trailing_commas=False)

                summary[model_name]["success"] += 1
            except Exception as e:
                summary[model_name]["fail"] += 1
                print(f"\n❌ Error with {java_file.name} + {model_name}: {e}")

            progress.update(1)


# ========== SUMMARY ==========
print("\n📊 Summary Report:\n")

table = [
    [model, stats["success"], stats["fail"]]
    for model, stats in summary.items()
]
print(tabulate(table, headers=[
      "Model", "Successes", "Failures"], tablefmt="fancy_grid"))
