const { gql } = require('apollo-server');

const actividadTypeDefs = gql `
    type Actividad {
        nombre: String!
        duracion: Int!
        categoria: Int!
        horarios: [Horario]!
        centros: [String]!
        horarioTmp: Horario! 
    }

    input ActividadInput {
        nombre: String!
        duracion: Int!
        categoria: Int!
        horarioTmp: Horario! 
    }
    
    extend type Query {
        actividadesByCentro(username: String!): [Actividad]
        actividadByNombre(nombre: String!): Actividad

    }

    extend type Mutation {
        createActividad(actividad: ActividadInput!): Actividad
        insertCentro(username: String!): Actividad
    }
`;

module.exports = actividadTypeDefs;