from django.db import models
from.city import City

class Address(models.Model):
	city = models.ForeignKey(City)