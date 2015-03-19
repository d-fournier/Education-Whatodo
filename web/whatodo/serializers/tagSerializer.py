from rest_framework import serializers
from whatodo.models.tag import Tag


class TagSerializer(serializers.ModelSerializer):
	class Meta:
		model = Tag
		fields = ('id', 'name',)
