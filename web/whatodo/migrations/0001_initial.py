# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
    ]

    operations = [
        migrations.CreateModel(
            name='Address',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('localisation', models.CharField(max_length=255)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Category',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True)),
                ('name', models.CharField(unique=True, max_length=255)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='City',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True)),
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
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Event',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True)),
                ('name', models.CharField(max_length=30)),
                ('description', models.CharField(max_length=200)),
                ('url', models.URLField()),
                ('startTime', models.TimeField()),
                ('endTime', models.TimeField()),
                ('startDate', models.DateField()),
                ('endDate', models.DateField()),
                ('price', models.DecimalField(max_digits=5, decimal_places=2)),
                ('min_age', models.IntegerField()),
                ('addresses', models.ManyToManyField(to='whatodo.Address')),
                ('categories', models.ManyToManyField(to='whatodo.Category')),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='Tag',
            fields=[
                ('id', models.AutoField(serialize=False, primary_key=True)),
                ('name', models.CharField(unique=True, max_length=255)),
            ],
            options={
            },
            bases=(models.Model,),
        ),
        migrations.CreateModel(
            name='User',
            fields=[
                ('id', models.AutoField(verbose_name='ID', serialize=False, auto_created=True, primary_key=True)),
                ('name', models.CharField(max_length=30)),
                ('email', models.EmailField(max_length=254)),
                ('categories', models.ManyToManyField(to='whatodo.Category')),
                ('cities', models.ManyToManyField(to='whatodo.City')),
                ('events', models.ManyToManyField(to='whatodo.Event')),
            ],
            options={
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
            field=models.ForeignKey(to='whatodo.User'),
            preserve_default=True,
        ),
        migrations.AddField(
            model_name='address',
            name='city',
            field=models.ForeignKey(to='whatodo.City'),
            preserve_default=True,
        ),
    ]
