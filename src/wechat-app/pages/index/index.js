var sliderWidth = 96; // 需要设置slider的宽度，用于计算中间位置

Page({
  data: {
    tabs: ["股票推荐"],
    activeIndex: 0,
    sliderOffset: 0,
    sliderLeft: 0,
    stocks: [{ 'stockcode': '000000', 'stockname': '加载中...','price':'0.0' }],
    datadate:'0000-00-00',
    inputShowed: false,
    inputVal: ""
  },
  onLoad: function () {
    wx.showLoading({
      title: '加载中',
    })
    var that = this;
    wx.getSystemInfo({
      success: function (res) {
        that.setData({
          sliderLeft: (res.windowWidth / that.data.tabs.length - sliderWidth) / 2,
          sliderOffset: res.windowWidth / that.data.tabs.length * that.data.activeIndex
        });
      }
    });
    wx.request({
      url: 'https://app.luxinx.com/stock/display/price',
      success:function(result){
        wx.hideLoading({
          title: '加载中',
        })
        if (result.statusCode == 200 && result['data'].length > 0){
          var dateobj = new Date(result.data[0].datecreated)
          var datestr = dateobj.getFullYear() + "-" + (dateobj.getMonth()+1) + "-" + dateobj.getDate();
          that.setData({
            stocks: result.data, datadate: datestr, size: result.data.length
          });
        }else{
          that.setData({
            stocks: [{ 'stockcode': '000000', 'stockname': '加载失败...' }]
          });
        }
        console.log(result);
      }
    })
    wx.getShareInfo({
      shareTicket: '帮你选股',
    })
  },
  onShareAppMessage:function(res){
      return {
        title: '帮你选股',
        path: '/pages/index/index?id=123',
        success: function (res) {
          wx.showToast({
            title: '分享成功',
          })
        }
      }
  },
  onPullDownRefresh:function(){

    wx.showLoading({
      title: '加载中',
    })
    var that= this;
    that.setData({
      stocks: [{ 'stockcode': '000000', 'stockname': '加载中...' }]
    });
    wx.request({
      url: 'https://app.luxinx.com/stock/display/price',
      success: function (result) {
        wx.hideLoading({
          title: '加载中',
        })
        if (result.statusCode == 200 && result['data'].length > 0) {
          var dateobj = new Date(result.data[0].datecreated)
          var datestr = dateobj.getFullYear() + "-" + (dateobj.getMonth() + 1) + "-" + dateobj.getDate();
          that.setData({
            stocks: result.data, datadate: datestr, size: result.data.length
          });
        } else {
          that.setData({
            stocks: [{ 'stockcode': '000000', 'stockname': '加载失败...' }]
          });
        }
        wx.stopPullDownRefresh();
        console.log(result);
      }
    })
  },
  tabClick: function (e) {
    this.setData({
      sliderOffset: e.currentTarget.offsetLeft,
      activeIndex: e.currentTarget.id
    });
  },
  showInput: function () {
    this.setData({
      inputShowed: true
    });
  },
  hideInput: function () {
    this.setData({
      inputVal: "",
      inputShowed: false
    });
  },
  clearInput: function () {
    this.setData({
      inputVal: ""
    });
  },
  inputTyping: function (e) {
   var inputvalue = e.detail.value;
   var sendlength = 99;
   if(parseInt(inputvalue)){
     sendlength = 5;
   }else{
     sendlength = 2;
   }

    this.setData({
      inputVal: e.detail.value
    });

    var that=this;
    if (inputvalue.length > sendlength || inputvalue.length==0){
      wx.showLoading({
        title: '加载中',
      })
      
      wx.request({
        url: 'https://app.luxinx.com/stock/display/price?param=' + encodeURI(inputvalue),
        success: function (result) {
          wx.hideLoading({
            title: '加载中',
          })
          if (result.statusCode == 200 && result['data'].length > 0) {
            var dateobj = new Date(result.data[0].datecreated)
            var datestr = dateobj.getFullYear() + "-" + (dateobj.getMonth() + 1) + "-" + dateobj.getDate();
            that.setData({
              stocks: result.data, datadate: datestr, size: result.data.length
            });
          } else {
            that.setData({
              stocks: [{ 'stockcode': '000000', 'stockname': '未查询到结果...' }]
            });
          }
        }
      })
    }

  }
});