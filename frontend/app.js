const API_BASE_URL = 'http://localhost:8081/api/students';

// Sidebar Coming Soon function
window.showComingSoon = (feature) => {
    showToast('info', `${feature} abhi demo version mein include nahi hai!`);
};

// DOM Elements
const studentsTableBody = document.getElementById('studentsTableBody');
const emptyState = document.getElementById('emptyState');
const studentsTable = document.getElementById('studentsTable');
const searchInput = document.getElementById('searchInput');

// Modals
const studentModal = document.getElementById('studentModal');
const deleteModal = document.getElementById('deleteModal');
const studentForm = document.getElementById('studentForm');

// Buttons
const openAddModalBtn = document.getElementById('openAddModalBtn');
const closeModalBtn = document.getElementById('closeModalBtn');
const cancelModalBtn = document.getElementById('cancelModalBtn');
const closeDeleteModalBtn = document.getElementById('closeDeleteModalBtn');
const cancelDeleteBtn = document.getElementById('cancelDeleteBtn');
const confirmDeleteBtn = document.getElementById('confirmDeleteBtn');

// State
let students = [];
let currentEditId = null;
let currentDeleteId = null;

// Initialize
document.addEventListener('DOMContentLoaded', fetchStudents);

// Fetch all students
async function fetchStudents() {
    try {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) throw new Error('Failed to fetch students');
        
        students = await response.json();
        renderTable(students);
    } catch (error) {
        showToast('error', 'Error loading students: ' + error.message);
    }
}

// Render Table
function renderTable(data) {
    studentsTableBody.innerHTML = '';
    
    if (data.length === 0) {
        studentsTable.classList.add('hidden');
        emptyState.classList.remove('hidden');
        return;
    }
    
    studentsTable.classList.remove('hidden');
    emptyState.classList.add('hidden');
    
    data.forEach(student => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>#${student.id}</td>
            <td><strong>${student.name}</strong></td>
            <td>${student.rollNumber}</td>
            <td><span class="badge">${student.course}</span></td>
            <td>${student.marks}%</td>
            <td>${student.attendance}%</td>
            <td>
                <button class="btn-icon" onclick="openEditModal(${student.id})" title="Edit">
                    <i class="fa-regular fa-pen-to-square"></i>
                </button>
                <button class="btn-icon delete" onclick="openDeleteModal(${student.id})" title="Delete">
                    <i class="fa-regular fa-trash-can"></i>
                </button>
            </td>
        `;
        studentsTableBody.appendChild(tr);
    });
}

// Search Filter
searchInput.addEventListener('input', (e) => {
    const term = e.target.value.toLowerCase();
    const filtered = students.filter(s => 
        s.name.toLowerCase().includes(term) || 
        s.rollNumber.toLowerCase().includes(term) || 
        s.course.toLowerCase().includes(term)
    );
    renderTable(filtered);
});

// Modal Logic
function openModal(title) {
    document.getElementById('modalTitle').textContent = title;
    studentModal.classList.add('show');
}

function closeModal() {
    studentModal.classList.remove('show');
    studentForm.reset();
    currentEditId = null;
    document.getElementById('studentId').value = '';
}

openAddModalBtn.addEventListener('click', () => openModal('Add New Student'));
closeModalBtn.addEventListener('click', closeModal);
cancelModalBtn.addEventListener('click', closeModal);

// Delete Modal Logic
function openDeleteModal(id) {
    currentDeleteId = id;
    deleteModal.classList.add('show');
}

function closeDeleteModal() {
    deleteModal.classList.remove('show');
    currentDeleteId = null;
}

closeDeleteModalBtn.addEventListener('click', closeDeleteModal);
cancelDeleteBtn.addEventListener('click', closeDeleteModal);

// Edit Student
window.openEditModal = (id) => {
    const student = students.find(s => s.id === id);
    if (!student) return;
    
    currentEditId = id;
    document.getElementById('studentId').value = student.id;
    document.getElementById('name').value = student.name;
    document.getElementById('rollNumber').value = student.rollNumber;
    document.getElementById('course').value = student.course;
    document.getElementById('marks').value = student.marks;
    document.getElementById('attendance').value = student.attendance;
    
    openModal('Edit Student');
};

// Form Submission (Add/Edit)
studentForm.addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const payload = {
        name: document.getElementById('name').value.trim(),
        rollNumber: document.getElementById('rollNumber').value.trim(),
        course: document.getElementById('course').value.trim(),
        marks: parseFloat(document.getElementById('marks').value),
        attendance: parseFloat(document.getElementById('attendance').value)
    };
    
    try {
        let response;
        if (currentEditId) {
            // Update
            response = await fetch(`${API_BASE_URL}/${currentEditId}`, {
                method: 'PUT',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        } else {
            // Create
            response = await fetch(API_BASE_URL, {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(payload)
            });
        }
        
        if (!response.ok) {
            const errData = await response.json();
            throw new Error(errData.message || 'Validation failed');
        }
        
        showToast('success', `Student successfully ${currentEditId ? 'updated' : 'added'}!`);
        closeModal();
        fetchStudents();
        
    } catch (error) {
        showToast('error', error.message);
    }
});

// Confirm Delete
confirmDeleteBtn.addEventListener('click', async () => {
    if (!currentDeleteId) return;
    
    try {
        const response = await fetch(`${API_BASE_URL}/${currentDeleteId}`, {
            method: 'DELETE'
        });
        
        if (!response.ok) throw new Error('Failed to delete student');
        
        showToast('success', 'Student deleted successfully');
        closeDeleteModal();
        fetchStudents();
        
    } catch (error) {
        showToast('error', error.message);
        closeDeleteModal();
    }
});

// Toast Notifications
function showToast(type, message) {
    const container = document.getElementById('toastContainer');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    
    const icon = type === 'success' ? 'fa-check-circle' : type === 'error' ? 'fa-circle-exclamation' : 'fa-info-circle';
    
    toast.innerHTML = `
        <i class="fa-solid ${icon}"></i>
        <span>${message}</span>
    `;
    
    container.appendChild(toast);
    
    // Remove after 3 seconds
    setTimeout(() => {
        toast.style.animation = 'slideInRight 0.3s ease-out reverse forwards';
        setTimeout(() => toast.remove(), 300);
    }, 3000);
}
