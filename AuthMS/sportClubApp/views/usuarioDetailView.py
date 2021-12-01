from rest_framework import generics

from sportClubApp.models.usuario import Usuario
from sportClubApp.serializers.usuarioSerializer import UsuarioSerializer

class UsuarioDetailView(generics.RetrieveAPIView):
    queryset =  Usuario.objects.all()
    serializer_class = UsuarioSerializer