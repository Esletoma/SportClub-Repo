const actividadResolver = {
    Query: {
        actividadesByCentro: async (_,{ username }, { dataSources, userIdToken }) => {      
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            usernameToken = usuario.username;
            
            if (usuario.tipo == 1){
                return await dataSources.actividadAPI.actividadesByCentro(usernameToken);
            }    
            else return null;
        },
        actividadByNombre: async (_,{ nombre }, { dataSources, userIdToken }) =>{
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            usernameToken = usuario.username;
            
            if (usuario.tipo == 1){
                return await dataSources.actividadAPI.actividadByNombre(nombre);
            }    
            else return null;
        },
    },
    Mutation: {
        createActividad: async(_, { actividad }, { dataSources, userIdToken }) => {
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            usernameToken = usuario.username;

            //tipo 0 = usuario  tipo 1 = centro
            if (usuario.tipo == 1){
                dataSources.actividadAPI.createActividad(actividad);
                return await dataSources.actividadAPI.insertCentro(actividad.nombre,usernameToken);
            }else{
                return null ;
            }
            
            
        },
    },
};
module.exports = actividadResolver;
