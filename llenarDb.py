import requests,random,json
names=['Jose','Luis','Juan','Manuel','Sofia','Sara']
lastNames=['Martinez','Lopez','Rosas','Gonzalez','Henao']
for i in range(0,100):
    name=names[random.randint(0,len(names)-1)]+lastNames[random.randint(0,len(lastNames)-1)]
    data={"name":name,"email":name+str(i)+"@gmail.com","password":name+"123#!R","phones":[{"number":str(random.randint(1000000000,9999999999)),"citycode":str(random.randint(0,99)),"countrycode":str(random.randint(0,200))}]}
    headers = {'Content-type': 'application/json'}
    requests.post("http://localhost:8080/api/registro",data=json.dumps(data),headers=headers)

