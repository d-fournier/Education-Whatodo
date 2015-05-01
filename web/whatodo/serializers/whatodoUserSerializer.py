from rest_framework import serializers
from whatodo.models.event import Event
from whatodo.models.category import Category
from whatodo.models.city import City
from whatodo.models.whatodoUser import WhatodoUser
from whatodo.serializers.eventSerializer import EventReadSerializer
from whatodo.serializers.categorySerializer import CategorySerializer
from whatodo.serializers.citySerializer import CitySerializer

class WhatodoUserSerializer(serializers.ModelSerializer):
	categories = CategorySerializer(many=True, read_only=True)
	cities = CitySerializer(many=True, read_only=True)
	events = EventReadSerializer(many=True, read_only=True)

	class Meta:
		model = WhatodoUser
		fields = ('id', 'email', 'username', 'age', 'categories', 'cities', 'events')

