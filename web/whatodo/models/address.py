from django.db import models
from.city import City

class Address(models.Model):
	localisation = models.CharField(max_length = 255)
	city = models.ForeignKey(City)

	def __str__(self):
		return self.localisation + " " + self.city