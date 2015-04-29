from rest_framework import serializers
from whatodo.models.city import City


class CitySerializer(serializers.ModelSerializer):
    class Meta:
        model = City
        fields = ('name', 'ZIPcode', 'latitude', 'longitude')
