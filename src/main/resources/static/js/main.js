var imagesApi=Vue.resource('/image{/id}');
//directory= 'src/main/resources/images';
//'<img :id="image.id" :src=src/main/resources/images'+ '/' + '{{image.filename}}>'+
//'<img src=src/main/resources/images/test1.png>'


/*Vue.component('interactive-form',{

    props: ['images'],
    data: function() {
        return {
            filename: ''
        }
    },
    template:
    '<div>' +
        '<input type="text" v-model="filename"/>'+
        '<input type="button" value="Save" @click="save"/>'+
        '<br><br>'+
    '</div>',
    methods:{
        save: function(){
            var image ={filename: this.filename};
            imagesApi.save({},image).then(result=>
                result.json().then(data=>{
                    this.images.push(data);
                     this.filename=''
                })

            )
        }
    }
})*/


Vue.component('image-view' ,{
    props: ['image'],
    computed: {
        miniatureFullPath: function(){
            return "http://localhost:8080/picture/"+this.image.id
        }
    },
    template:
    '<div>'+
        '{{image.id}})'+
        '<img :src=miniatureFullPath width="200" height="87" @click="increase">'+
        '<input type="button" value="delete" @click="del">'+
    '</div>',
    methods:{
        increase: function(){

        }
        del: function(){
            imagesApi.remove({id: image.id}).then(result=>{
            if(result.ok)
            })
        }
    }
})
//http://localhost:8080/image/smallCopy/


Vue.component('image-list', {
    props: ['images'],
    template:
    '<div>'+
        '<interactive-form :images="images"/>'+
        '<image-view v-for="image in images" :key="image.id" :image="image"/>'+
    '</div>',
    created: function(){
        imagesApi.get().then(result=>
        result.json().then(data=>
        data.forEach(image=>this.images.push(image)))
        )
    }
})


var app = new Vue({
    el: '#app',
    template: '<image-list :images="images"/>',

    data: {
    images:[

    ]
    }
})