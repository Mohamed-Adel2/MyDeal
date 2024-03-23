var selectedCategoryNow;
document.addEventListener('DOMContentLoaded', function () {
    console.log("listener");
    getCategoreiesFromServlet();

});
const DeleteCategoryButton = document.getElementById('DeleteCategory');
const AddCategoryButton = document.getElementById('AddCategory');
DeleteCategoryButton.addEventListener('click', function() {
   deleteCategoryFromServlet();
});
AddCategoryButton.addEventListener('click',function (){
    addCategoryToServlet();
})
function getCategoreiesFromServlet(){
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function () {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status === 200) {
                var jsonResponse = xhr.responseText;
                var categories = JSON.parse(jsonResponse);
                console.log(jsonResponse);
                appearCategories(categories);
            } else {
                console.error('Request failed: ' + xhr.status);
            }
        }
    };

    xhr.open('GET', 'categories', true);

    xhr.send();
}
function appearCategories(categories){
  //  categories = ["Category1 ", "Category2", "Category3"];
    const categoryContainer = document.getElementById('categoryContainer');

    categories.forEach((category, index) => {

        const radioButton = document.createElement('input');
        radioButton.setAttribute('type', 'radio');
        radioButton.setAttribute('name', 'category');
        radioButton.setAttribute('id', category);
        radioButton.setAttribute('value', category);


        const label = document.createElement('label');
        label.setAttribute('for', category);
        label.textContent = category;

        const div = document.createElement('div');
        div.classList.add('form-check');
        div.appendChild(radioButton);
        div.appendChild(label);
        categoryContainer.querySelector('fieldset').appendChild(div);
        radioButton.addEventListener('change', function() {
            const selectedCategory = this.value;
            console.log('Selected category:', selectedCategory);
            selectedCategoryNow = selectedCategory;

        });
    });

}
function deleteCategoryFromServlet(){
    var  jsonData = {
        "categoryName":selectedCategoryNow,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'admin/adminDeleteCategory';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                alert('Category Deleted successfully');
            } else {
                alert('Failed Delete Category: ' + responseData.message);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function() {

        console.error('Request failed');
    };
    xhr.send(JSON.stringify(jsonData));
}
function addCategoryToServlet(){
    const CategoryName = document.getElementById('CategoryName').value;
    var  jsonData = {
        "categoryName":CategoryName,
    };
    const xhr = new XMLHttpRequest();
    const servletURL = 'admin/adminAddCategory';
    xhr.open('POST', servletURL, true);
    xhr.setRequestHeader('Content-Type', 'application/json');

    xhr.onload = function() {
        if (xhr.status >= 200 && xhr.status < 300) {
            const responseData = JSON.parse(xhr.responseText);
            console.log('Response from server:', responseData);

            if (responseData.success) {
                alert('Category Deleted successfully');
            } else {
                alert('Failed Delete Category: ' + responseData.message);
            }
        } else {
            console.error('Request failed with status:', xhr.status);
        }
    };

    xhr.onerror = function() {

        console.error('Request failed');
    };
    xhr.send(JSON.stringify(jsonData));
}