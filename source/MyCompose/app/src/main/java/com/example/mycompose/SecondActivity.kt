package com.example.mycompose

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.compose.foundation.Image
import androidx.compose.foundation.Text
import androidx.compose.foundation.clickable
import androidx.compose.foundation.drawBorder
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.state
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawShadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.setContent
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.LiveData

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

        }
    }

    val product = Product(1, "apple", 12f)


    @Composable
    fun ProductLabel(product: Product) {
        Text("${product.id}, name: ${product.name}, price: ${product.price}")
    }

    @Composable
    fun ProductLabel1(product: Product) {
        if (product.price > 0)
            Text("${product.id}, name: ${product.name}, price: ${product.price}")
    }

    @Composable
    fun ProductLabel2(products: List<Product>) {
        Column() {
            for (product in products)
                Text("${product.name}")
        }
    }

    @Composable
    fun ProductFilter(products: List<Product>) {
        var filter by state { "" }

        val (shape, setShape) = state<Shape> {
            CircleShape
        }
        Image(
            imageResource(id = R.drawable.meinv),
            modifier = Modifier
                .size(256.dp)
                .padding(16.dp)
                .drawShadow(8.dp, shape)
                .drawBorder(6.dp, MaterialTheme.colors.primary, shape)
                .drawBorder(12.dp, MaterialTheme.colors.secondary, shape)
                .drawBorder(18.dp, MaterialTheme.colors.background, shape)
                .clickable {
                    setShape(
                        if(shape == CircleShape)
                            CutCornerShape(topLeft = 32.dp,bottomRight = 32.dp)
                        else
                            CircleShape
                    )
                }

        )


        Column() {
            for (product in products)
                Text("${product.name}")
        }
    }

    @Composable
    fun ShoppingCar(shoppingCart: LiveData<List<Product>>){
       ConstraintLayout(constraintSet = ConstraintSet {

       }) {

       }
    }
}


data class Product(val id: Int, val name: String = "unknown", val price: Float = 0f)