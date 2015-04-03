from django.db import models
from .category import Category
from .city import City 
from .event import Event

class User(models.Model):
	name = models.CharField (max_length = 30)
	email = models.EmailField (max_length = 254)
	
	def __str__(self):
		return self.name

	categories = models.ManyToManyField(Category)
	cities = models.ManyToManyField(City)
	events = models.ManyToManyField(Event)

