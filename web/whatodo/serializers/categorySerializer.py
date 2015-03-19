from rest_framework import serializers
from whatodo.models.category import Category


class CategorySerializer(serializers.ModelSerializer):
	class Meta:
		model = Category
		fields = ('id', 'name',)
