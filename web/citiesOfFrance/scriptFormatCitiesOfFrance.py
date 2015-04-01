import json
obj  = json.load(open("citiesOfFrance.json"))
data = {}
i = 0

# Iterate through the objects in the JSON and create                      
# datas with the items we found.                                                      
for item in obj: 
	data[i]= {}
	data[i]['ZIPcode'] = item['code postal']
	data[i]['City'] = item['VILLE']
	i = i+1

# Output the updated file with pretty JSON                                      
open("formatCitiesOfFrance", "w").write(
    json.dumps(data, sort_keys=True, indent=4, separators=(',', ': '))
)