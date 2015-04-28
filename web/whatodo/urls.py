from django.conf.urls import url, include
from rest_framework import routers
from .views import *
from django.conf import settings
from django.conf.urls.static import static

router = routers.DefaultRouter()
router.register(r'event', EventViewSet)
router.register(r'category', CategoryViewSet)
router.register(r'tag', TagViewSet)
router.register(r'city', CityViewSet)


# Wire up our API using automatic URL routing.
# Additionally, we include login URLs for the browsable API.
urlpatterns = [
	url(r'^api/', include(router.urls)),
	#url(r'^'),
	url(r'^api/api-auth/', include('rest_framework.urls', namespace='rest_framework')),
	url(r'^$', 'whatodo.views.home', name='home'),
] 