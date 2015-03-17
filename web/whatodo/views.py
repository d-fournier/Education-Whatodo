from rest_framework import viewsets
from .serializers.eventSerializer import EventSerializer
from .models.event import Event

class EventViewSet(viewsets.ModelViewSet):
    """
    API endpoint that allows users to be viewed or edited.
    """
    queryset = Event.objects.all()
    serializer_class = EventSerializer

# Create your views here.
def home(request):
	return render(request, 'whatodo/index.html')