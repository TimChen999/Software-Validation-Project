import requests

TOKEN = "squ_61cbd23edda99d5cff82f5a8656c44679132a594"
PROJECT_KEY = "VAL-Project"
BASE_URL = "http://localhost:9000"

session = requests.Session()
session.auth = (TOKEN, '')

page = 1
page_size = 500
issues = []

while True:
    url = f"{BASE_URL}/api/issues/search?componentKeys={PROJECT_KEY}&p={page}&ps={page_size}"
    resp = session.get(url).json()
    issues.extend(resp['issues'])

    if page * page_size >= resp['total']:
        break
    page += 1

with open("issues.txt", "w") as f:
    for issue in issues:
        path = issue.get("component", "unknown")
        line = issue.get("line", "?")
        message = issue.get("message", "no message")
        f.write(f"{path} | Line {line}: {message}\n")

print(f"Wrote {len(issues)} issues to issues.txt")
