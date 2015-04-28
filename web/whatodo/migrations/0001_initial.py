# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations
import django.utils.timezone


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
<<<<<<< HEAD
            name='Address',
            fields=[
                ('id', models.AutoField(auto_created=True, verbose_name='ID', serialize=False, primary_key=True)),
                ('localisation', models.CharField(max_length=255)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
=======
>>>>>>> 86b73ffc8cc72b95a3452a5ac6def1d4ccf26a19
            name='Category',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255, unique=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='City',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=50)),
                ('ZIPcode', models.CharField(max_length=10)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Comment',
            fields=[
<<<<<<< HEAD
                ('id', models.AutoField(auto_created=True, verbose_name='ID', serialize=False, primary_key=True)),
=======
                ('id', models.AutoField(primary_key=True, auto_created=True, serialize=False, verbose_name='ID')),
>>>>>>> 86b73ffc8cc72b95a3452a5ac6def1d4ccf26a19
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=30)),
                ('description', models.CharField(max_length=200)),
                ('url', models.URLField()),
                ('startTime', models.TimeField()),
                ('endTime', models.TimeField()),
                ('startDate', models.DateField()),
                ('endDate', models.DateField()),
                ('price', models.DecimalField(decimal_places=2, max_digits=5)),
                ('min_age', models.IntegerField()),
                ('address', models.CharField(max_length=500)),
                ('categories', models.ManyToManyField(to='whatodo.Category')),
                ('city', models.ForeignKey(to='whatodo.City')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Tag',
            fields=[
                ('id', models.AutoField(primary_key=True, serialize=False)),
                ('name', models.CharField(max_length=255, unique=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='WhatodoUser',
            fields=[
<<<<<<< HEAD
                ('password', models.CharField(verbose_name='password', max_length=128)),
                ('last_login', models.DateTimeField(verbose_name='last login', default=django.utils.timezone.now)),
                ('id', models.AutoField(serialize=False, primary_key=True)),
                ('email', models.EmailField(unique=True, max_length=254)),
                ('username', models.CharField(unique=True, max_length=30)),
                ('is_admin', models.BooleanField(help_text='Designates whether the user can log into this admin site.', default=False)),
                ('is_active', models.BooleanField(help_text='Designates whether this user should be treated as active. Unselect this instead of deleting accounts.', default=True)),
                ('date_joined', models.DateTimeField(default=django.utils.timezone.now)),
=======
                ('id', models.AutoField(primary_key=True, auto_created=True, serialize=False, verbose_name='ID')),
                ('name', models.CharField(max_length=30)),
                ('email', models.EmailField(max_length=254)),
>>>>>>> 86b73ffc8cc72b95a3452a5ac6def1d4ccf26a19
                ('categories', models.ManyToManyField(to='whatodo.Category')),
                ('cities', models.ManyToManyField(to='whatodo.City')),
                ('events', models.ManyToManyField(to='whatodo.Event')),
            ],
            options={
                'abstract': False,
            },
            bases=(models.Model,),
        ),
        migrations.AddField(
            model_name='event',
            name='tags',
            field=models.ManyToManyField(to='whatodo.Tag'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='comment',
            name='event',
            field=models.ForeignKey(to='whatodo.Event'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='comment',
            name='user',
            field=models.ForeignKey(to='whatodo.WhatodoUser'),
            preserve_default=True,
        ),
    ]
