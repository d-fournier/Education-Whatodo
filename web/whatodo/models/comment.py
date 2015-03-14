from django.db import models
from .user import User
from .event import Event

class Comment(models.Model):
	user = models.ForeignKey(User)
	event = models.ForeignKey(Event)