from rest_framework import serializers
from whatodo.models.category import Category


class CategorySerializer(serializers.HyperlinkedModelSerializer):
	class Meta:
		model = Category
		fields = ('name')
