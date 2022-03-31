package jp.techacademy.matsuo.yuuta.apiapp

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_web_view.*

class WebViewActivity : AppCompatActivity(),FragmentCallback {

    private val viewPagerAdapter by lazy { ViewPagerAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        var shop: Shop = intent.getSerializableExtra("SHOP") as Shop
        webView.loadUrl(shop.couponUrls.sp)

        //お気に入りボタンの処理
        // お気に入り状態を取得
        val isFavorite = FavoriteShop.findBy(shop.id) != null
        fab.setImageResource(if (isFavorite) R.drawable.ic_star else R.drawable.ic_star_border)
        fab.setOnClickListener {
            if(isFavorite) {
                onDeleteFavorite(shop.id)
                fab.setImageResource(R.drawable.ic_star_border)
            } else {
                onAddFavorite(shop)
                fab.setImageResource(R.drawable.ic_star)
            }
        }
    }

    companion object {
        fun start(activity: Activity, shop: Shop) {
            var intent = Intent(activity, WebViewActivity::class.java)
            intent.putExtra("SHOP", shop)
            activity.startActivity(intent)
        }
    }

    override fun onClickItem(shop: Shop) {
        TODO("Not yet implemented")
    }

    override fun onAddFavorite(shop: Shop) {
        FavoriteShop.insert(FavoriteShop().apply {
            id = shop.id
            name = shop.name
            imageUrl = shop.logoImage
            url = if (shop.couponUrls.sp.isNotEmpty()) shop.couponUrls.sp else shop.couponUrls.pc
        })
    }

    override fun onDeleteFavorite(id: String) {
        FavoriteShop.delete(id)
    }

}