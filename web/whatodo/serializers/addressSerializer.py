from rest_framework import serializers
from whatodo.models.address import Address


class AddressSerializer(serializers.HyperlinkedModelSerializer):
    class Meta:
        model = Address
        fields = ('localisation')
