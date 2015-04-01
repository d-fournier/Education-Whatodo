import fileinput
import re

dataFile = open("formatCitiesOfFrance2.json", "r")
whole_thing = dataFile.read()
dataFile.close()

pathFichierResultats = "resultats.tmp"
pathFichierResultatsFINAUX = "formatCitiesOfFrance_FinalVersion"

# Magouille pour réussir ensuite à bien indenter et placer le pk
fichierResultats = open(pathFichierResultats, 'w')
whole_thing = re.sub(r'},\n','},', whole_thing)
fichierResultats.write(whole_thing)
fichierResultats.close()

fichierResultats = open(pathFichierResultats, 'w')
whole_thing = re.sub(r'},    },    "','},\n\t\t"pk": id\n\t},\n\t"', whole_thing)
fichierResultats.write(whole_thing)
fichierResultats.close()
# Fin Magouille

# Enlever les id au début de l'objet json
fichierResultats = open(pathFichierResultats, 'w')
whole_thing = re.sub(r'"[0-9]+": {','{', whole_thing)
fichierResultats.write(whole_thing)
fichierResultats.close()

# Mettre une pk à chaque ville (sauf la dernière : la faire à la main)
id = 0
f1 = open(pathFichierResultats, 'r')
f2 = open(pathFichierResultatsFINAUX, 'w')
for line in f1:
	if (line.find("id") != -1):
		id=id+1
	f2.write(line.replace('id', str(id)))
print ('Il y a ' + str(id) + ' villes dans notre base de données !')
f1.close()
f2.close()

print ("\nFin du script. Bonne journée.")