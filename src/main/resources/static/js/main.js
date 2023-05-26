var imagesApi=Vue.resource('/image{/id}');
var pathToImages="http://localhost:8080/picture/"


function bytesToBase64(bytes) {
  let binary = '';
  let len = bytes.byteLength;
  for (let i = 0; i < len; i++) {
    binary += String.fromCharCode(bytes[i]);
  }
  return window.btoa(binary);
}

Vue.component('only-image',{
    props: ['image'],
    template:
        '<img :src="imageSrc" alt="">',
    computed:{
        imageSrc: function() {
            return 'data:image/png;base64,' + bytesToBase64(this.image);
        }
    }

})

Vue.component('image-data-row' ,{
    props: ['image','images'],
    computed: {
        miniatureFullPath: function(){
            return pathToImages+this.image.id
        }
    },
    template:
    '<tr>'+
        '<td><only-image :image="image" key="image.id"/></td>'+
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
        },
        createImageUrl: function(imageBytes) {
            let blob = new Blob([imageBytes], { type: 'image/png' });

            return URL.createObjectURL(blob);
        },
        imageSrc(imageBytes) {
          return 'data:image/png;base64,' + btoa(new Uint8Array(imageBytes).reduce((data, byte) => data + String.fromCharCode(byte), ''));
        }
    }
})

Vue.component('image-list', {
    props: ['images'],
    template:
    '<div>'+
        '<add-form :images="images"/>'+
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
                '<image-data-row v-for="image in images" :key="image.id" :image="image" :images="images"/>'+
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
        },
        uploadFile: function(){}
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