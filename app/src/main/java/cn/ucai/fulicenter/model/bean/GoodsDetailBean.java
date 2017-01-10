package cn.ucai.fulicenter.model.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2017/1/9 0009.
 */

public class GoodsDetailBean implements Serializable{

    /**
     * id : 10
     * goodsId : 7258
     * catId : 262
     * goodsName : 防晒精华露
     * goodsEnglishName : JANESCE
     * goodsBrief : 规格：60ml 4周唤醒人体潜能 养成肌肤自然防晒力 Janesce有机护肤创始人Janice女士多年研究成果，利用特殊草本植物、花卉及坚果油等混合萃取物提升人气肌肤对阳光伤害的自然防御力。富含草本抗氧化剂，来对抗由于日光曝晒肌肤产生的游离基；乳木果油帮助过滤日光，不易引起肌肤敏感；抗氧化剂和维他命A都能储存在肌肤表层，并增加肌肤对抗阳光伤害的抵抗力。 适合一年四季使用，或在晚冬、早春阳光开始强烈前开始使用。无需卸妆，孕幼人群，光敏感肌适用。
     * shopPrice : ￥788
     * currencyPrice : ￥788
     * promotePrice : ￥749
     * rankPrice : ￥749
     * isPromote : true
     * goodsThumb : 201508/thumb_img/7258_thumb_G_1440746999353.jpg
     * goodsImg : 201508/thumb_img/7258_thumb_G_1440746999353.jpg
     * addTime : 1476768710669
     * shareUrl : http://m.fulishe.com/item/7258
     * properties : [{"id":8942,"goodsId":0,"colorId":1,"colorName":"灰色","colorCode":"#959595","colorImg":"201309/1380064809234134935.jpg","colorUrl":"1","albums":[{"pid":7258,"imgId":27035,"imgUrl":"201508/goods_img/7258_P_1440746999045.jpg","thumbUrl":"no_picture.gif"}]}]
     * promote : true
     */

    private int id;
    private int goodsId;
    private int catId;
    private String goodsName;
    private String goodsEnglishName;
    private String goodsBrief;
    private String shopPrice;
    private String currencyPrice;
    private String promotePrice;
    private String rankPrice;
    private boolean isPromote;
    private String goodsThumb;
    private String goodsImg;
    private long addTime;
    private String shareUrl;
    private boolean promote;
    private List<PropertiesBean> properties;

    public GoodsDetailBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsEnglishName() {
        return goodsEnglishName;
    }

    public void setGoodsEnglishName(String goodsEnglishName) {
        this.goodsEnglishName = goodsEnglishName;
    }

    public String getGoodsBrief() {
        return goodsBrief;
    }

    public void setGoodsBrief(String goodsBrief) {
        this.goodsBrief = goodsBrief;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }

    public String getCurrencyPrice() {
        return currencyPrice;
    }

    public void setCurrencyPrice(String currencyPrice) {
        this.currencyPrice = currencyPrice;
    }

    public String getPromotePrice() {
        return promotePrice;
    }

    public void setPromotePrice(String promotePrice) {
        this.promotePrice = promotePrice;
    }

    public String getRankPrice() {
        return rankPrice;
    }

    public void setRankPrice(String rankPrice) {
        this.rankPrice = rankPrice;
    }

    public boolean isIsPromote() {
        return isPromote;
    }

    public void setIsPromote(boolean isPromote) {
        this.isPromote = isPromote;
    }

    public String getGoodsThumb() {
        return goodsThumb;
    }

    public void setGoodsThumb(String goodsThumb) {
        this.goodsThumb = goodsThumb;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public long getAddTime() {
        return addTime;
    }

    public void setAddTime(long addTime) {
        this.addTime = addTime;
    }

    public String getShareUrl() {
        return shareUrl;
    }

    public void setShareUrl(String shareUrl) {
        this.shareUrl = shareUrl;
    }

    public boolean isPromote() {
        return promote;
    }

    public void setPromote(boolean promote) {
        this.promote = promote;
    }

    public List<PropertiesBean> getProperties() {
        return properties;
    }

    public void setProperties(List<PropertiesBean> properties) {
        this.properties = properties;
    }

    public static class PropertiesBean {
        /**
         * id : 8942
         * goodsId : 0
         * colorId : 1
         * colorName : 灰色
         * colorCode : #959595
         * colorImg : 201309/1380064809234134935.jpg
         * colorUrl : 1
         * albums : [{"pid":7258,"imgId":27035,"imgUrl":"201508/goods_img/7258_P_1440746999045.jpg","thumbUrl":"no_picture.gif"}]
         */

        private String colorImg;
        private String colorUrl;
        private List<AlbumsBean> albums;

        public String getColorImg() {
            return colorImg;
        }

        public void setColorImg(String colorImg) {
            this.colorImg = colorImg;
        }

        public String getColorUrl() {
            return colorUrl;
        }

        public void setColorUrl(String colorUrl) {
            this.colorUrl = colorUrl;
        }

        public List<AlbumsBean> getAlbums() {
            return albums;
        }

        public void setAlbums(List<AlbumsBean> albums) {
            this.albums = albums;
        }

        public static class AlbumsBean {
            /**
             * pid : 7258
             * imgId : 27035
             * imgUrl : 201508/goods_img/7258_P_1440746999045.jpg
             * thumbUrl : no_picture.gif
             */

            private int pid;
            private int imgId;
            private String imgUrl;
            private String thumbUrl;

            public int getPid() {
                return pid;
            }

            public void setPid(int pid) {
                this.pid = pid;
            }

            public int getImgId() {
                return imgId;
            }

            public void setImgId(int imgId) {
                this.imgId = imgId;
            }

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getThumbUrl() {
                return thumbUrl;
            }

            public void setThumbUrl(String thumbUrl) {
                this.thumbUrl = thumbUrl;
            }
        }

    }

    @Override
    public String toString() {
        return "GoodsDetailBean{" +
                "addTime=" + addTime +
                ", id=" + id +
                ", goodsId=" + goodsId +
                ", catId=" + catId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsEnglishName='" + goodsEnglishName + '\'' +
                ", goodsBrief='" + goodsBrief + '\'' +
                ", shopPrice='" + shopPrice + '\'' +
                ", currencyPrice='" + currencyPrice + '\'' +
                ", promotePrice='" + promotePrice + '\'' +
                ", rankPrice='" + rankPrice + '\'' +
                ", isPromote=" + isPromote +
                ", goodsThumb='" + goodsThumb + '\'' +
                ", goodsImg='" + goodsImg + '\'' +
                ", shareUrl='" + shareUrl + '\'' +
                ", promote=" + promote +
                ", properties=" + properties +
                '}';
    }
}
