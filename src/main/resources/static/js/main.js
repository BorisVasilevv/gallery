var imagesApi=Vue.resource('/image{/id}');
var pathToImages="http://localhost:8080/picture/"

Vue.component('image-view' ,{
    props: ['image','images'],
    computed: {
        miniatureFullPath: function(){
            return pathToImages+this.image.id
        }
    },
    template:
    '<tr>'+
        '<td><img :src=miniatureFullPath width="200" height="87" @click="increase"></td>'+
        '<td>{{image.size}}</td>'+
        '<td>{{image.date}}</td>'+
        '<td><input type="button" value="delete" @click="del"/></td>'+
    '</tr>',
    methods:{
        increase: function(){

        },
        del: function(){
            imagesApi.remove({id:this.image.id}).then(result=>{
                if(result.ok){
                    this.images.splice(this.images.indexOf(this.image), 1)
                }
            })
        }
    }
})

Vue.component('image-list', {
    props: ['images'],
    template:
    '<div>'+
        '<add-form :im="images"/>'+
        '<hr>'+
        '<table class="table">'+
            '<thead>'+
                '<tr>'+
                    '<td>Miniature</td>'+
                    '<td>Size</td>'+
                    '<td>Date</td>'+
                    '<td>Action</td>'+
                '</tr>'+
            '</thead>'+

            '<tbody>'+
                '<image-view v-for="image in images" :key="image.id" :image="image" :images="images"/>'+
            '</tbody>'+
        '</table>'+
    '</div>',
    created: function(){
        imagesApi.get().then(result=>
        result.json().then(data=>
        data.forEach(image=>this.images.push(image)))
        )
    }
})


Vue.component('add-form',{

    props: ['images'],
    data: function() {
        return {
            file: ''
        }
    },
    template:
    '<div>'+
        '<input type="file" @change="uploadFile" ref="file"/>'+
        '<input type="button" value="Add" @click="save"/>'+
    '</div>',
    methods:{
        save: function(){
            var image ={filename: this.filename};
            imagesApi.save({},image).then(result=>
                result.json().then(data=>{
                    this.images.push(data);
                     this.file=''
                })

            )
        }
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