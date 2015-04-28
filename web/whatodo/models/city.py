from django.db import models

class City(models.Model):
	id = models.AutoField(primary_key=True)
	
	name = models.CharField(max_length = 50)
	ZIPcode = models.CharField(max_length = 10)
	latitude = models.DecimalField(max_digits=10, decimal_places=7)
	longitude = models.DecimalField(max_digits=10, decimal_places=7)

	def __str__(self):
		return self.name + " (" + self.ZIPcode + ")"

