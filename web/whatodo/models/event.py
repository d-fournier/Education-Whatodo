from django.db import models
from .tag import Tag
from .category import Category
from .city import City
from django.conf import settings
from django.core.files.storage import FileSystemStorage

fs = FileSystemStorage(location=settings.MEDIA_ROOT, base_url=settings.MEDIA_URL)

class Event(models.Model):
	id = models.AutoField(primary_key=True)
	
	name = models.CharField(max_length = 30)
	description = models.CharField (max_length = 200)
	url = models.URLField()
	imageEvent = models.ImageField(storage=fs, default='/static/images/jeanDujardin.jpg')
	#TODO: image or Video field, do we really require a model for that?
	startTime = models.TimeField(auto_now = False, auto_now_add = False)
	endTime = models.TimeField(auto_now = False, auto_now_add = False)
	startDate = models.DateField(auto_now = False, auto_now_add = False) 
	endDate = models.DateField(auto_now = False, auto_now_add = False) 
	price = models.DecimalField(max_digits = 5, decimal_places = 2)
	min_age = models.IntegerField()
	

	address = models.CharField (max_length = 500)
	city = models.ForeignKey(City)
	latitude = models.DecimalField(max_digits=10, decimal_places=7)
	longitude = models.DecimalField(max_digits=10, decimal_places=7)
	
	categories = models.ManyToManyField(Category)
	#organizer = models.ForeignKey(User) 
	tags = models.ManyToManyField(Tag)

	def __str__(self):
		return self.name




  
