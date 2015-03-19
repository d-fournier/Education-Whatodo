# -*- coding: utf-8 -*-
from __future__ import unicode_literals

from django.db import models, migrations


class Migration(migrations.Migration):

    dependencies = [
        ('whatodo', '0001_initial'),
    ]

    operations = [
        migrations.RemoveField(
            model_name='address',
            name='city',
        ),
        migrations.AddField(
            model_name='address',
            name='localisation',
            field=models.CharField(max_length=255, default=0),
            preserve_default=False,
        ),
        migrations.AddField(
            model_name='tag',
            name='name',
            field=models.CharField(max_length=255, default=''),
            preserve_default=False,
        ),
    ]
