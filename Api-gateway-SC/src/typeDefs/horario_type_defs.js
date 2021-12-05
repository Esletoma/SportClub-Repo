const { gql } = require('apollo-server');
const accountTypeDefs = gql `

    type Horario {
        id: String!
        dia: String!
        hora: Int!
        min: Int! 
    }

    input HorarioInput {
        dia: String!
        hora: Int!
        min: Int!
    }

    extend type Query {
        horarios(): [Horario]
        horariosByDia(dia: String!): [Horario]

    }

    extend type Mutation {
        createHorario(horario: HorarioInput!): Horario
    }

`;
module.exports = horarioTypeDefs;