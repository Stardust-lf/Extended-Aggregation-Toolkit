import requests

data = {'file': open('clientfile/2010.pdf', 'rb')}
rr = requests.post('http://127.0.0.1:5000/upload', files=data)
head = {'User-Agent': 'python-requests/2.31.0',
        'Accept-Encoding': 'gzip, deflate, br',
        'Accept': '*/*',
        'Connection': 'keep-alive',
        }
rr = requests.get('http://127.0.0.1:5000/upload',headers=head)
#print(rr.text)
#rr = requests.post('http://127.0.0.1:5000/upload')
print(rr.text)
