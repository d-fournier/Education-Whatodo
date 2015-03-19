from django.conf.urls import url, include
from rest_framework import routers
from .views import *

router = routers.DefaultRouter()
router.register(r'event', EventViewSet)


# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
	url(r'^api/', include(router.urls)),
	url(r'^api/api-auth/', include('rest_framework.urls', namespace='rest_framework')),
	url(r'^$', 'whatodo.views.home', name='home'),
]