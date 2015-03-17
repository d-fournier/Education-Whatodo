from rest_framework import serializers
from whatodo.models.event import Event


class EventSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Event
        fields = ('name', 'description', 'url')