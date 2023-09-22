import requests

data = {'file': open('./left.java', 'rb')}
rr = requests.post('http://127.0.0.1:40000/upload', files=data)
