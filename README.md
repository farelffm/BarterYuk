# 📦 BarterYuk - Aplikasi Tukar Barang (Marketplace Barter)

**BarterYuk** adalah aplikasi Android berbasis Kotlin yang dirancang untuk mempermudah pengguna dalam melakukan tukar-menukar barang (barter) secara modern. Aplikasi ini mengusung estetika **Material Design 3** dengan sentuhan *gradient* yang elegan dan navigasi yang intuitif.

---

## 🚀 Fitur & Pembaruan Terbaru

- **Desain Visual Modern**: Menggunakan tema *Vertical Gradient* (#E3F2FD ke #90CAF9) dengan sudut kartu yang sangat membulat (*high corner radius* 20dp-32dp).
- **Home Screen Terstruktur**: Urutan konten yang dioptimalkan: 
  `Search Bar` ➔ `Banner Iklan (Sliding)` ➔ `Rekomendasi (Horizontal)` ➔ `CTA Panel` ➔ `Kategori` ➔ `Semua Barang (Grid)`.
- **Banner Interaktif**: Implementasi `ViewPager2` dengan *Custom Dot Indicators* untuk promosi atau informasi terbaru.
- **Sistem Kategori Dinamis**: *Horizontal Scroll* kategori dengan 10+ kategori lengkap (Elektronik, Fashion, Hobi, Musik, dll).
- **Item Card Redesign**: Desain kartu barang baru menggunakan sistem *overlay*:
  - **Top-Left**: Ikon kategori transparan.
  - **Top-Right**: Ikon kondisi barang.
  - **Bottom**: Nama barang dan pemilik dengan latar belakang *translucent black*.
- **Floating Navigation Bar**: Bottom Navigation yang melayang dengan desain modern untuk akses menu utama.

---

## 🛠️ Teknologi yang Digunakan

- **Language**: Kotlin 
- **Architecture**: MVVM (Model-View-ViewModel)
- **UI Framework**: XML Layouts dengan **ViewBinding** & **Material Design 3**
- **Navigation**: Jetpack Navigation Component (SafeArgs)
- **Components**: 
  - `ViewPager2` (Banner Slide)
  - `RecyclerView` (Grid & Horizontal Layout)
  - `NestedScrollView` (Smooth UI Experience)
  - `ConstraintLayout` & `MaterialCardView`

---

## 📁 Struktur Proyek (Terupdate)

```text
app/src/main/java/com/example/barteryuk/
├── adapter/
│   ├── BarterAdapter.kt        # Grid "Semua Barang"
│   ├── RecommendationAdapter.kt # List "Rekomendasi"
│   └── BannerAdapter.kt        # Slider "Banner Iklan"
├── model/
│   └── BarterItem.kt           # Data class utama barang
└── ui/
    ├── MainViewModel.kt        # Pusat data (LiveData)
    ├── HomeFragment.kt         # Dashboard (Urutan elemen baru)
    ├── ProfileFragment.kt      # Manajemen Akun & "Barang Saya"
    └── ...                     # Auth & Detail Fragments
```

---

## 📝 Catatan Pengembangan

Proyek ini difokuskan pada **User Experience (UX)** yang mulus. Urutan elemen di halaman utama disusun sedemikian rupa untuk meningkatkan *engagement* pengguna, mulai dari pencarian, inspirasi (rekomendasi), hingga eksplorasi kategori.

Setiap komponen UI seperti `item_barter.xml` dan `fragment_home.xml` telah dioptimalkan agar responsif dan mengikuti standar desain Android terbaru.

---

*Dibuat oleh Farel Fazriel Marbella [24082010159] untuk keperluan Tugas/Presentasi Pengembangan Aplikasi Mobile.*
