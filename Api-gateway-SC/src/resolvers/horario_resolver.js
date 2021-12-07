const horarioResolver = {
    Query: {
        horarios: async (_, { dataSources, userIdToken }) => {
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            if (usuario.tipo == 1)
                return dataSources.ActividadAPI.getHorarios();
            else
                return null;

        },
        horariosByDia: async (_,{dia}, { dataSources, userIdToken }) => {
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            if (usuario.tipo == 1)
                return dataSources.ActividadAPI.horariosByDia(dia);
            else
                return null;

        },
    },
    Mutation: {
        createHorario: async(_, { horario }, { dataSources, userIdToken }) => {
            usuario = (await dataSources.authAPI.getUser(userIdToken));
            usernameToken = usuario.username;

            //tipo 0 = usuario  tipo 1 = centro
            if (usuario.tipo == 1){
                return dataSources.actividadAPI.createHorario(horario);
            }else{
                return null ;
            }
            
            
        },
    },
};
module.exports = horarioResolver;