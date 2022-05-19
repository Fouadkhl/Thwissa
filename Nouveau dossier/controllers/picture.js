const Product = require('../models/schema');


const getPictures = async (req, res) => {
    const pictures = await Product.find(req.query)
      .sort('-like')
      .limit(3);
  
    res.status(200).json({ pictures });
  };
  
  module.exports = {
    getPictures,
  };
  