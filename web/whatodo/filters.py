import django_filters
from .models.event import Event

class EventFilter(django_filters.FilterSet):
	min_price = django_filters.NumberFilter(name="price", lookup_type='gte')
	max_price = django_filters.NumberFilter(name="price", lookup_type='lte')
	max_date = django_filters.DateFilter(name='endDate', lookup_type='lte')
	min_date = django_filters.DateFilter(name='startDate', lookup_type='gte')
	max_hour = django_filters.TimeFilter(name='endTime', lookup_type='lte')
	min_hour = django_filters.TimeFilter(name='startTime', lookup_type='gte')
	class Meta:
		model = Event
		fields = ['min_date', 'max_date', 'min_price', 'max_price', 'min_hour', 'max_hour']