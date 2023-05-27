var imagesApi=Vue.resource('/image{/id}');

Vue.component('image-data-row' ,{
    props: ['image','images'],
    template:
        '<tr>'+
            '<td><img :src="imageSrc(image.base64image, image.extension)" height="90" wight="200" alt="" @click="openImage(imageSrc(image.base64image, image.extension))"></td>'+
            '<td>{{image.size}}</td>'+
            '<td>{{getTime(image.date)}}<br>{{getDate(image.date)}}</td>'+
            '<td><input type="button" value="delete" @click="del"/></td>'+
        '</tr>',
    methods:{
        openImage: function(url) {
            // Создаем элементы для модального окна и изображения
            const overlay = document.createElement('div');
            const image = document.createElement('img');

            // Настраиваем стили элементов модального окна
            overlay.style.position = 'fixed';
            overlay.style.top = '0';
            overlay.style.left = '0';
            overlay.style.width = '100%';
            overlay.style.height = '100%';
            overlay.style.backgroundColor = 'rgba(0, 0, 0, 0)';
            overlay.style.zIndex = '1';
            overlay.style.display = 'flex';
            overlay.style.alignItems = 'center';
            overlay.style.justifyContent = 'center';

            // Настраиваем атрибуты изображения
            image.src = url;
            image.style.maxWidth = '90%';
            image.style.maxHeight = '90%';

            // Добавляем изображение в модальное окно
            overlay.appendChild(image);

            // Добавляем модальное окно в документ
            document.body.appendChild(overlay);

            // Закрываем модальное окно при клике на любом месте наложения
            overlay.addEventListener('click', () => {
            overlay.remove();
            });
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
        },
        getDate(date){
            let arr=date.split('T')[0].split('-');
            return arr[2]+'.'+arr[1]+'.'+arr[0];
        },
        getTime(date){
            return date.split('+').join('T').split('.').join('T').split('T')[1];
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
        data.forEach(image=>this.images.push(image))))
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


                imagesApi.save({},image).then(result=>
                    result.json().then(data=>{
                        vm.images.push(data);
                }));
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


var app = new Vue({
    el: '#app',
    template: '<image-list :images="images"/>',

    data: {
        images:[]
    }
})