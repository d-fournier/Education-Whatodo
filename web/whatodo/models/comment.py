from django.db import models
from .whatodoUser import WhatodoUser
from .event import Event

class Comment(models.Model):
	user = models.ForeignKey(WhatodoUser)
	event = models.ForeignKey(Event)
	