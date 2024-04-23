package com.example.myjetpackcompose.ui.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myjetpackcompose.MainActivity
import com.example.myjetpackcompose.R
import com.example.myjetpackcompose.ui.theme.MyJetPackComposeTheme

@Composable
fun LoginForm() {
    Surface(color = Color(R.color.login)) {
        var credentials by remember { mutableStateOf(Credentials()) }
        val context = LocalContext.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp)
        ) {
            Image(painter = painterResource(id = R.drawable.ic_login_banner),
                modifier = Modifier.size(200.dp), contentDescription = "")
            LoginField(
                value = credentials.login, onChange = {data->credentials=credentials.copy(login = data)},
                modifier = Modifier.fillMaxWidth(), "Login", "Enter User Name"
            )
            Spacer(modifier = Modifier.height(10.dp))
            PasswordField(
                value = credentials.password,
                onChange = {data->credentials=credentials.copy(password = data) },
                submit = {
                         if (!checkCredentials(credentials, context)) credentials=Credentials()
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            LabelCheckBox(label = "Remember Me", onCheckChanged = {  credentials = credentials.copy(isRemember = !credentials.isRemember)}, credentials.isRemember)
            Spacer(modifier = Modifier.height(20.dp))
            Button( onClick = {
                if (!checkCredentials(credentials, context)) credentials = Credentials()
            },
                enabled = credentials.ifNotEmpty(), shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(text = "Login")
            }
        }
    }
}


@Composable
fun LoginField(
    value: String,
    onChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Login",
    placeholder: String = "Enter User Name"
) {

    val focusManager = LocalFocusManager.current
    val userNameIcon = @Composable {
        Icon(
            Icons.Default.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    TextField(value = value,
        onValueChange = onChange,
        leadingIcon = userNameIcon,
        modifier = modifier,
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        keyboardActions = KeyboardActions(
            onNext = { focusManager.moveFocus(FocusDirection.Down) }
        ),
        placeholder = { Text(text = placeholder) },
        label = { Text(text = label) },
        singleLine = true,
        visualTransformation = VisualTransformation.None
    )
}

@Composable
fun PasswordField(
    value: String,
    onChange: (String) -> Unit,
    submit: () -> Unit,
    modifier: Modifier = Modifier,
    label: String = "Password",
    placeholder: String = "Enter User Password"
) {

    var isPasswordVisible by remember { mutableStateOf(false) }
    val passwordLeadingIcon = @Composable {
        Icon(
            Icons.Default.Key,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
        )
    }
    val passwordTrailIcon = @Composable {
        IconButton(onClick = {isPasswordVisible=!isPasswordVisible}){
        Icon(
            if(isPasswordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.primary
            )
        }

    }

    TextField(
        value = value,
        onValueChange = onChange,
        modifier = modifier,
        leadingIcon = passwordLeadingIcon,
        trailingIcon = passwordTrailIcon,
        placeholder = { Text(placeholder) },
        label = { Text(text = label) },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(onDone = { submit }),
        singleLine = true,
        visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),

        )
}

@Composable
fun LabelCheckBox(label:String, onCheckChanged:() -> Unit,
                  isChecked:Boolean){
    Row (
        Modifier
            .clickable(onClick = onCheckChanged)
            .padding(4.dp)

    ){
        Checkbox(checked = isChecked, onCheckedChange =null )
        Spacer(Modifier.size(6.dp))
        Text(label)
    }

}

data class Credentials(var login:String="", var password:String="", var isRemember:Boolean=false){
    fun ifNotEmpty():Boolean {
        return login.isNotEmpty() && password.isNotEmpty()
    }
}



fun checkCredentials(credentials: Credentials,context: Context):Boolean{
    if (credentials.ifNotEmpty() && credentials.login=="admin"){
        context.startActivity(Intent(context,MainActivity::class.java))
        (context as Activity).finish()
        return true
    }else {
        Toast.makeText(context,"Wrong Credientals",Toast.LENGTH_LONG).show()
        return false
    }
}
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginActivityPreviewDark() {
    MyJetPackComposeTheme {
        LoginForm()
    }
}