from rest_framework import serializers
from whatodo.models.address import Address


class AddressSerializer(serializers.ModelSerializer):
    class Meta:
        model = Address
        fields = ('localisation','city',)
