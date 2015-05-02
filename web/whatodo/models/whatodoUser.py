from django.db import models
from django.utils import timezone
from django.utils.http import urlquote
from django.utils.translation import ugettext_lazy as _
from django.core.mail import send_mail
from django.contrib.auth.models import (
	BaseUserManager, AbstractBaseUser
)

from .category import Category
from .city import City 
from .event import Event
from django.conf import settings

fs = FileSystemStorage(location=settings.MEDIA_ROOT, base_url=settings.MEDIA_URL)

class WhatodoUserManager(BaseUserManager):
	def _create_user(self, email, password, is_admin, **extra_fields):
		if not email:
			raise ValueError('Users must have an email address')

		user = self.model(
			email = self.normalize_email(email),
			is_admin = is_admin,
			**extra_fields 
		)
		user.set_password(password)
		user.save(using = self._db)
		return user

	def create_user(self, email, password = None, **extra_fields):
		return self._create_user(email, password, False, **extra_fields)

	def create_superuser(self, email,password, **extra_fields):
		return self._create_user(email, password, True, **extra_fields)



class WhatodoUser(AbstractBaseUser):	
	id = models.AutoField(primary_key=True)
	email = models.EmailField(max_length = 254, unique = True)
	username = models.CharField(max_length=30)
	age = models.IntegerField(default = 0)
	imageUser =  models.ImageField(storage=fs, default='/static/images/jeanDujardin.jpg')
	is_admin = models.BooleanField(default=False,
		help_text=_('Designates whether the user can log into this admin '
					'site.'))
	is_active = models.BooleanField(default=True,
		help_text=_('Designates whether this user should be treated as '
					'active. Unselect this instead of deleting accounts.'))
	date_joined = models.DateTimeField(default=timezone.now)

	categories = models.ManyToManyField(Category)
	cities = models.ManyToManyField(City)
	events = models.ManyToManyField(Event)

	objects = WhatodoUserManager()

	USERNAME_FIELD = 'email'
	REQUIRED_FIELDS = []

	def get_full_name(self):
		return self.username

	def get_short_name(self):        
		return self.username

	def __str__(self):             
		return self.username

	def has_perm(self, perm, obj=None):
		return True

	def has_module_perms(self, app_label):
		return True

	@property
	def is_staff(self):
		return self.is_admin
