# Generated by Django 3.2.7 on 2021-09-24 06:03

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('app2', '0002_person'),
    ]

    operations = [
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.BigAutoField(auto_created=True, primary_key=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
                ('email', models.CharField(max_length=30)),
            ],
        ),
    ]
