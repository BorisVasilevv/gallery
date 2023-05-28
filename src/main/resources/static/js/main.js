var imagesApi=Vue.resource('/image{/id}');

Vue.component('image-list', {
    data() {
        return {
            selectedSortValue: 'By date',
            selectedSortDirection: 'Descending'
        }
    },
    props: ['images'],
    template:
        '<div>'+
            '<add-form :images="images"/>'+
            '<br>'+
            '<form>'+
                'Filter:  '+
                '<select v-model="selectedSortValue">'+
                    '<option>By date</option>'+
                    '<option>By size</option>'+
                '</select>  '+
                '<select v-model="selectedSortDirection">'+
                    '<option>Ascending</option>'+
                    '<option>Descending</option>'+
                '</select>  '+
            '</form>'+
            '<hr>'+
            '<div class="grid">'+
                '<image-data-row v-for="image in selectedItems" :key="image.id" :image="image" :images="images"/>'+
            '</div>'+
        '</div>',
    created: function(){
        imagesApi.get().then(result=>
        result.json().then(data=>
        data.forEach(image=>this.images.push(image))))
    },
    computed: {
        selectedItems(){
            if(this.selectedSortValue=="By size"){
                if(this.selectedSortDirection=="Ascending") return quickSortBySize(this.images);
                else if (this.selectedSortDirection=="Descending") return quickSortBySize(this.images).reverse();
            }
            else if (this.selectedSortValue=="By date"){
                if(this.selectedSortDirection=="Ascending") return quickSortByDate(this.images);
                else if (this.selectedSortDirection=="Descending") return quickSortByDate(this.images).reverse();
            }

        }
    }
})

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
            '<input type="file" id="inputFile" accept="image/*">'+
            '<button @click="save">Add</button>'+
        '</div>',
    methods:{
        save: function(){
            let file = document.querySelector("#inputFile").files[0];
            let vm = this;
            fileToBase64(file, function(base64String){
                vm.base64image=base64String;
                let fileSize = file.size;
                const currentDate = new Date();
                const extension = file.name.split('.').pop();
                let image ={size: fileSize, date: currentDate, base64image: vm.base64image, extension: extension};

                imagesApi.save({},image).then(result=>
                    result.json().then(data=>{
                        vm.images.push(data);
                }));
            });
            document.querySelector('#inputFile').value = null;

        }
    }
})


Vue.component('image-data-row' ,{
    props: ['image','images'],
    template:
        '<div>'+
            '<div class="aspect-ratio-box">'+
                '<img :src="imageSrc(image.base64image, image.extension)" alt="" @click="openImage(imageSrc(image.base64image, image.extension))">'+
            '</div>'+
            '{{image.size}}<br>{{getTime(image.date)}}<br>{{getDate(image.date)}}<br>'+
            '<input type="button" value="delete" @click="del"/>'+
        '</div>',
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
            overlay.style.backgroundColor = 'rgba(19, 19, 19, .75)';
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


function fileToBase64(file, callback) {
    const reader = new FileReader();
    reader.onload = function(event) {
    const base64String = event.target.result.replace("data:" + file.type + ";base64,", "");
    callback(base64String);
    };
    reader.readAsDataURL(file);
}

function quickSortBySize(arr){
    if(arr.length<=1){
        return arr;
    }
    let pivot=arr[0];
    let left=[];
    let right=[];
    for(let i=1;i<arr.length;i++){
        if(arr[i].size<pivot.size){
            left.push(arr[i]);
        }
        else {
            right.push(arr[i]);
        }
    }
    return quickSortBySize(left).concat(pivot, quickSortBySize(right));
}


function quickSortByDate(arr){
    if(arr.length<=1){
        return arr;
    }
    let pivot=arr[0];
    let left=[];
    let right=[];
    for(let i=1;i<arr.length;i++){
        if(arr[i].date<pivot.date){
            left.push(arr[i]);
        }
        else {
            right.push(arr[i]);
        }
    }
    return quickSortByDate(left).concat(pivot, quickSortByDate(right));
}


var app = new Vue({
    el: '#app',
    template: '<image-list :images="images"/>',

    data: {
        images:[]
    }
})