from django.db import models
from .tag import Tag
from .category import Category
from .city import City
from django.conf import settings



class Event(models.Model):
	id = models.AutoField(primary_key=True)
	
	name = models.CharField(max_length = 30)
	description = models.CharField (max_length = 200)
	url = models.URLField()
	imageEvent = models.ImageField(upload_to = settings.IMAGES_URL, default='/static/images/jeanDujardin.jpg')
	#TODO: image or Video field, do we really require a model for that?
	startTime = models.TimeField(auto_now = False, auto_now_add = False)
	endTime = models.TimeField(auto_now = False, auto_now_add = False)
	startDate = models.DateField(auto_now = False, auto_now_add = False) 
	endDate = models.DateField(auto_now = False, auto_now_add = False) 
	price = models.DecimalField(max_digits = 5, decimal_places = 2)
	min_age = models.IntegerField()
	

	address = models.CharField (max_length = 500)
	city = models.ForeignKey(City)
	categories = models.ManyToManyField(Category)
	#organizer = models.ForeignKey(User) 
	tags = models.ManyToManyField(Tag)

	def __str__(self):
		return self.name




  