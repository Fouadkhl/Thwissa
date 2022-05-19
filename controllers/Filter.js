const { query } = require('express')
const Agency1 = require('../models/agency1')
const Trips = require('../models/trips')

filtrage = async(req,res)=>{
  try {
    const {atributs,wilaya,rate} = req.query
    
    if(atributs == 'agency'){
        
       
        let queryobject = {}
        let sortList
        const sort = ("rate","location")
        sortList = sort.split(",").join(" ")
 
        if(wilaya){
          queryobject.location = wilaya
          sortList = ("rate")
        }
        if(rate){
         queryobject.rate = {$gte : rate}
         sortList = ("rate")
        }


     const result = await Agency1.find(queryobject).sort(sortList)
     res.status(200).json({
       result ,
       nbHits : result.length})
    }
    
    
    else if(atributs == "trips"){
        const {numericFilter,after,wilaya,rate} = req.query
        let queryobject = {}
        let sortList
        const sort = ("rate","location")
        sortList = sort.split(",").join(" ")
 
        if(wilaya){
          queryobject.location = wilaya
          sortList = ("rate")
        }
        if(rate){
         queryobject.rate = {$gte : rate}
         sortList = ("rate")
        }
        if(numericFilter){
          
            const operatorMap ={
                '>': '$gt',
                '>=': '$gte',
                '=': '$eq',
                '<': '$lt',
                '<=': '$lte'
            }
            const regex = /\b(>|>=|=|<|<=)\b/g
            console.log(numericFilter);
            let filters = numericFilter.replace(regex,(match)=>`-${operatorMap[match]}-`)
            console.log(filters);
            const options = ['minprice','maxprice','minduration','maxduration']
            
            filters = filters.split(',').forEach((item) => 
            {
                const [field , operator , value]= item.split('-')
                if(options.includes(field)){
                    queryobject[field] = {[operator]: Number(value)}
                }

            })
            console.log(queryobject);
        }
        if(after){
          const num = Number(after)
          
          const adddate = (date, period)=>{
            return date.setDate(date.getDate()+ period)
          }
          let thedate = new Date()
          adddate(thedate,num)
          queryobject.date = {$gte : thedate}
          console.log(queryobject);

        }
     const result = await Trips.find(queryobject).sort(sortList)
     res.status(200).json({
       result ,
       nbHits : result.length})
    }

  } catch (error) {
      res.status(400).json({error})
  } 
   









   /* let { search} = req.query
   const queryobject = {}
   const userid = req.cookies.userid
   const userinfo = await agency1.findOne({_id: userid})
   let sortList
   if(search){
     if(search.charAt(0)=== '@'){
         search = search.substring(1)
         queryobject.tags = { $regex: search, $options: 'i' }
     }else{
         queryobject.text = { $regex: search, $options: 'i' };
     }
     let sort = ('likes','dislikes','questionDate')
     sortList = sort.split(',').join(' ')
   }else{
     queryobject.tags = userinfo.location
     sortList = 'questionDate'
   }
   
   
   const questions = await Quetions.find(queryobject).sort(sortList)
     res.status(200).json({questions , nbHits: questions.length })*/
 }


 module.exports = {
    filtrage
 }