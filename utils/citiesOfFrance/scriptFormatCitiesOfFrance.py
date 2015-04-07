import json
import re
obj  = json.load(open("citiesOfFrance.json"))
file = "cities.dump.json"
data = {}
complete_data = {}
i = 0

# Iterate through the objects in the JSON and create                      
# datas with the items we found.                                                      
for item in obj:
	if 'longitude' in item and 'latitude' in item:
		data[i]= {}
		data[i]['model'] = "whatodo.city"
		data[i]['fields'] = {}
		data[i]['fields']['ZIPcode'] = item['code postal']
		data[i]['fields']['name'] = item['VILLE']
		data[i]['fields']['longitude'] = item['longitude']
		data[i]['fields']['latitude'] = item['latitude']
		data[i]['pk'] = i
		i = i+1
# Output the string with pretty JSON                                      
string = json.dumps(data, sort_keys=True, indent=4, separators=(',', ': '))
# Enlever les id au début de l'objet json
string = re.sub(r'"[0-9]+": {','{', string)
open(file, "w").write(
    string
)