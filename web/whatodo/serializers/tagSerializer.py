from rest_framework import serializers
from whatodo.models.tag import Tag


class TagSerializer(serializers.HyperlinkedModelSerializer):
	class Meta:
		model = Tag
		fields = ('name')
