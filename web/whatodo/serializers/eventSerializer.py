from rest_framework import serializers
from whatodo.models.event import Event
from whatodo.models.address import Address
from whatodo.models.category import Category
from whatodo.models.tag import Tag
from whatodo.serializers.addressSerializer import AddressSerializer
from whatodo.serializers.tagSerializer import TagSerializer
from whatodo.serializers.categorySerializer import CategorySerializer

class EventSerializer(serializers.ModelSerializer):
	addresses = AddressSerializer(many=True)
	categories = CategorySerializer(many=True)
	tags = TagSerializer(many=True)

	class Meta:
		model = Event
		fields = ('id', 'name', 'description', 'url', 'startTime', 'endTime', 'startDate', 'endDate', 'price', 'min_age', 'addresses', 'categories', 'tags')

	def create(self, validated_data):
		addresses_data = validated_data.pop('addresses')
		categories_data = validated_data.pop('categories')
		tags_data = validated_data.pop('tags')
		event = Event.objects.create(**validated_data)
		for add_data in addresses_data :
			add = Address.objects.create(**add_data)
			event.addresses.add(add)
		for cat_data in categories_data :
			cat = Category.objects.create(**cat_data)
			event.categories.add(cat)
		for tag_data in tags_data :
			tag = Tag.objects.create(**tag_data)
			event.tags.add(tag)
		return event