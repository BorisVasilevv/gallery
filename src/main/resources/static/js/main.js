var imagesApi=Vue.resource('/image{/id}');
var pathToImages="http://localhost:8080/picture/"

const toBase64 = file => new Promise((resolve, reject) => {
    const reader = new FileReader();
    reader.readAsDataURL(file);
    reader.onload = () => resolve(reader.result);
    reader.onerror = reject;
});


Vue.component('image-data-row' ,{
    props: ['image','images'],
    template:
    '<tr>'+
        '<td><img :src="imageSrc(image.base64image, image.extension)" height="90" wight="200" alt=""></td>'+
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
        imageSrc(imageBytes, type) {
            return 'data:image/'+ type +';base64,' + imageBytes;
        }
    }
})

Vue.component('image-list', {
    props: ['images'],
    template:
    '<div>'+
        '<add-form :images="images"/>'+
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

//'<add-form :images="images"/>'+
//'<hr>'+

Vue.component('add-form',{
    props: ['images'],
    data: function(){
        return {
            size: null,
            date: null,
            base64image: null,
            extension: null
        }
    },
    template:
        '<div>'+
            '<input type="file" id="file" accept="image/*">'+
            '<button @click="save">Add</button>'+
        '</div>',
    methods:{
        save: function(){
            let file = document.querySelector("#file").files[0];
            let vm = this;
            fileToBase64(file, function(base64String){
                vm.base64image=base64String;
                let fileSize = file.size;
                const currentDate = new Date();
                const extension = file.name.split('.').pop();
                var image ={size: fileSize, date: currentDate, base64image: vm.base64image, extension: extension};

                imagesApi.save({},image).then(data => {
                     vm.images.push(data);
                });
            });

        },
        uploadImage: function(){
            let file = document.querySelector("#file").files[0];
            let data = new FormData();
            const fileContent = fs.readFileSync(filePath); // читаем содержимое файла
            const base64String = Buffer.from(fileContent).toString('base64');

            data.append("file", file);
            fetch("/image", {
                method: "POST",
                body: data
            });

        }
    }
})

function fileToBase64(file, callback) {
    const reader = new FileReader();
    reader.onload = function(event) {
    const base64String = event.target.result.replace("data:" + file.type + ";base64,", "");
    callback(base64String);
    };
    reader.readAsDataURL(file);
}

/*function uploadImage() {
    let file = document.querySelector("#file").files[0];
    let data = new FormData();
    data.append("file", file);
    fetch("/image", {
        method: "POST",
        body: data
    });
    fetch("/image", {
        method: "GET"
    });
    .then(response => {
        if (response.ok) {
            let message = document.querySelector("#message");
            message.textContent = "File uploaded: " + file.name;
            message.style.color = "green";
            loadImages();
        } else {
            let message = document.querySelector("#message");
            message.textContent = "Failed to upload file";
            message.style.color = "red";
        }
    })
    .catch(error => {
        console.error(error);
        let message = document.querySelector("#message");
        message.textContent = "Failed to upload file";
        message.style.color = "red";
    });
}*/


var app = new Vue({
    el: '#app',
    template: '<image-list :images="images"/>',

    data: {
    images:[

    ]
    }
})